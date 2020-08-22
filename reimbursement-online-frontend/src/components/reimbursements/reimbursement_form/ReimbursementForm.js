import { ExclamationCircleOutlined } from '@ant-design/icons';
import { Button, Card, DatePicker, Form, Input, Modal, notification } from 'antd';
import React, { Component } from 'react';
import ReimbursementService from '../../../services/ReimbursementService';

const { confirm } = Modal;

export class ReimbursementForm extends Component {

    formRef = React.createRef();

    onFinish = (values) => {

        ReimbursementService.submitReimbursement(values).then(response => {
            notification.success({
                message: 'Success',
                description: "You're successfully submit reimbursement."
            });
            this.formRef.current.resetFields();
        }).catch(error => {
            notification.error({
                message: 'Error',
                description: error.response.data.message
            });
        })

    }

    handleMenu = () => {
        this.props.handleSelectedMenu('reimbursement-form');
    }

    componentDidMount() {
        this.handleMenu();
    }

    showConfirm = (values) => {
        confirm({
            title: 'Reimbursement Form',
            icon: <ExclamationCircleOutlined />,
            content: 'Do you want to submit?',
            onCancel() { },
            onOk: () => {
                this.onFinish(values);
            }
        })
    }

    render() {
        return (
            <Card title="Reimbursement Form" >
                <Form {...layout} ref={this.formRef} onFinish={this.showConfirm}>
                    <Form.Item
                        label="Date of Purchase"
                        name="date_purchase"
                        rules={[
                            {
                                required: true,
                                message: 'Please input date purchase!'
                            }
                        ]}
                    >
                        <DatePicker />
                    </Form.Item>

                    <Form.Item
                        label="Amount"
                        name="amount"
                        rules={[
                            {
                                required: true,
                                message: 'Please input amount!'
                            }
                        ]}
                    >
                        <Input addonBefore={"Rp"} />
                    </Form.Item>

                    <Form.Item
                        label="Description"
                        name="description"
                        rules={[
                            {
                                required: true,
                                message: 'Please input description!'
                            },
                            {
                                max: 255,
                                message: 'Max description are 255 characters'
                            }
                        ]}
                    >
                        <Input.TextArea autoSize={{ minRows: 4, maxRows: 4 }} maxLength={255} />
                    </Form.Item>

                    <Form.Item {...tailLayout}>
                        <Button type="primary" htmlType="submit">Submit</Button>
                    </Form.Item>
                </Form>
            </Card>
        );
    }

};

const layout = {
    labelCol: {
        xl: { span: 4 },
        lg: { span: 6 },
        md: { span: 10 },
        sm: { span: 12 },
        xs: { span: 24 }
    },
    wrapperCol: {
        span: 8
    },
};

const tailLayout = {
    wrapperCol: {
        xl: { offset: 4, span: 8 },
        lg: { offset: 6, span: 8 },
        md: { offset: 10, span: 8 },
        sm: { offset: 12, span: 8 },
        xs: { span: 8 }
    },
};
