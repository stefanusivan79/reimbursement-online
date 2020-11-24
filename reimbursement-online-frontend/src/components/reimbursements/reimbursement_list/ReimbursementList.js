import { Button, Popover, Table } from 'antd';
import moment from 'moment';
import React, { Component } from 'react';
import NumberFormat from 'react-number-format';
import ReimbursementService from '../../../services/ReimbursementService';
import TagLabel from '../../common/TagLabel';

const columns = [
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
    title: 'Status',
    dataIndex: 'status',
    render: status => <TagLabel status={status} />,
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
    dataIndex: 'attachments',
    render: (attachments) => {
      const listAttachment = attachments.map(item => <Button type={'link'}
                                                             onClick={() => downloadFile(item)}>{item.name_original}</Button>);
      const content = <div style={{ display: 'flex', flexDirection: 'column' }}>{listAttachment}</div>

      return <Popover placement="bottomRight" content={content} title="Attachments"
                      trigger="click">Attachments</Popover>;
    }
  }
];

const downloadFile = (file) => {
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

const constructParams = params => {
  return {
    page: params.pagination.current,
    limit: params.pagination.pageSize,
    sortField: params.sortField,
    sortOrder: params.sortOrder
  };
};

export class ReimbursementList extends Component {

  state = {
    data: [],
    pagination: {
      current: 1,
      pageSize: 10,
    },
    loading: false,
  };

  handleMenu = () => {
    this.props.handleSelectedMenu('reimbursement-list');
  }

  componentDidMount() {
    const { pagination } = this.state;
    this.fetch({ pagination });

    this.handleMenu();
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
      params: constructParams(params)
    };

    ReimbursementService.getReimbursementList(config).then(response => {
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
        columns={columns}
        rowKey={data => data.secure_id}
        dataSource={data}
        pagination={pagination}
        loading={loading}
        onChange={this.handleTableChange}
      />
    );
  }
}

export default ReimbursementList
