import { Button, Popover, Space, Table } from 'antd';
import moment from 'moment';
import React, { Component } from 'react';
import NumberFormat from 'react-number-format';
import ReimbursementService from '../../../services/ReimbursementService';
import { STATUS__REIMBURSEMENT_ON_PROGRESS, STATUS__REIMBURSEMENT_REJECTED } from '../../../utils/Constants';
import { STATUS__REIMBURSEMENT_COMPLETED } from './../../../utils/Constants';
import TagLabel from './../../common/TagLabel';

class ReimbursementListAdmin extends Component {

    state = {
        data: [],
        pagination: {
            current: 1,
            pageSize: 10,
        },
        loading: false,
    };

    columns = [
        {
            title: 'Date Purchase',
            dataIndex: 'date_purchase',
            render: epoch => moment.unix(epoch).format('DD MMM YYYY'),
            sorter: true,
        },
        {
            title: 'Creation Date',
            dataIndex: 'creation_date',
            render: epoch => moment.unix(epoch).format('DD MMM YYYY'),
        },
        {
            title: 'Employee',
            dataIndex: 'employee_name',
        },
        {
            title: 'Status',
            dataIndex: 'status',
            render: status => {
                return <TagLabel status={status} />
            },
        },
        {
            title: 'Amount',
            dataIndex: 'amount',
            sorter: true,
            align: 'right',
            render: amount => <NumberFormat value={amount} displayType="text" thousandSeparator={true} prefix={"Rp"} />
        },
        {
            title: "Description",
            dataIndex: 'description'
        },
        {
            title: 'Action',
            render: (record) => {
                if (record.status === STATUS__REIMBURSEMENT_REJECTED || record.status === STATUS__REIMBURSEMENT_COMPLETED) {
                    return null;
                }

                if (record.status === STATUS__REIMBURSEMENT_ON_PROGRESS) {
                    return <Button type="primary" shape="round" onClick={() => this.complete(record.secure_id)}>Complete</Button>
                }

                return (
                    <Space size='middle'>
                        <Button type="primary" shape="round" onClick={() => this.accept(record.secure_id)}>Accept</Button>
                        <Button type="primary" shape="round" onClick={() => this.reject(record.secure_id)}>Reject</Button>
                    </Space>
                )
            },
        },
        {
            dataIndex: 'attachments',
            render: (attachments) => {
                const listAttachment = attachments.map(item => <Button type={'link'} key={item.file_name}
                                                                       onClick={() => this.downloadFile(item)}>{item.name_original}</Button>);
                const content = <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'self-start' }}>{listAttachment}</div>

                return <Popover placement="bottomRight" content={content} title="Attachments"
                                trigger="click"><u><i>Attachments</i></u></Popover>;
            }
        },
    ];

    reject = (secureId) => {
        this.setState({ loading: true });

        const config = {
            params: {
                reimbursementId: secureId
            }
        };

        ReimbursementService.rejectReimbursement(config)
            .then(response => {
                this.setState({
                    data: []
                })
                this.setState({
                    loading: false,
                    data: response.data.data,
                    pagination: {
                        current: response.data.current_page,
                        pageSize: response.data.limit,
                        total: response.data.total
                    },
                });
            }).catch(error => {
                this.setState({
                    loading: false
                })
            })
    }

    downloadFile = (file) => {
        const body = {
            name_original: file.name_original,
            file_name: file.file_name,
            bucket: file.bucket
        }

        ReimbursementService.downloadReimbursementAttachment(body)
          .then(response => {
              const url = window.URL.createObjectURL(new Blob([response.data]));
              const link = document.createElement('a');
              link.href = url;
              link.setAttribute('download', body.name_original); //or any other extension
              document.body.appendChild(link);
              link.click();
          });
    }

    accept = (secureId) => {
        this.setState({ loading: true });

        const config = {
            params: {
                reimbursementId: secureId
            }
        };

        ReimbursementService.acceptReimbursement(config)
            .then(response => {
                this.setState({
                    data: []
                })
                this.setState({
                    loading: false,
                    data: response.data.data,
                    pagination: {
                        current: response.data.current_page,
                        pageSize: response.data.limit,
                        total: response.data.total
                    },
                });
            }).catch(error => {
                this.setState({
                    loading: false
                })
            })
    }

    complete = (secureId) => {
        this.setState({ loading: true });

        const config = {
            params: {
                reimbursementId: secureId
            }
        };

        ReimbursementService.completeReimbursement(config)
            .then(response => {
                this.setState({
                    data: []
                })
                this.setState({
                    loading: false,
                    data: response.data.data,
                    pagination: {
                        current: response.data.current_page,
                        pageSize: response.data.limit,
                        total: response.data.total
                    },
                });
            }).catch(error => {
                this.setState({
                    loading: false
                })
            })
    }

    constructParamsPagination = params => {
        return {
            page: params.pagination.current,
            limit: params.pagination.pageSize,
            sortField: params.sortField,
            sortOrder: params.sortOrder
        };
    };

    handleMenu = () => {
        this.props.handleSelectedMenu('reimbursement-list-admin');
    }

    componentDidMount() {
        const { pagination } = this.state;
        this.fetch({ pagination });

        this.handleMenu();
    }

    componentWillUnmount() {
        this.setState({
            pagination: {
                current: 1,
                pageSize: 10,
            }
        })
    }

    handleTableChange = (pagination, filters, sorter) => {
        sorter.field = (sorter.field === 'date_purchase') ? 'datePurchase' : sorter.field;
        this.fetch({
            sortField: sorter.field,
            sortOrder: sorter.order,
            pagination,
            ...filters,
        });
    };

    fetch = (params = {}) => {
        this.setState({ loading: true });

        const config = {
            params: this.constructParamsPagination(params)
        };

        ReimbursementService.getAllReimbursementList(config)
            .then(response => {
                this.setState({
                    loading: false,
                    data: response.data.data,
                    pagination: {
                        ...params.pagination,
                        total: response.data.total
                    },
                });
            })
    };

    render() {
        const { data, pagination, loading } = this.state;
        return (
            <Table
                title={() => <h2>Reimbursement List</h2>}
                columns={this.columns}
                rowKey={data => data.secure_id}
                dataSource={data}
                pagination={pagination}
                loading={loading}
                onChange={this.handleTableChange}
            />
        );
    }
}

export default ReimbursementListAdmin;
