import { Button, Card, Form, Input, notification } from 'antd';
import React from 'react';
import EmployeeService from './../../../services/EmployeeService';

export const EditPersonalDetail = (props) => {
    const { email, full_name, bank_account } = props.dataEmployee;

    const onFinish = (values) => {
        EmployeeService.changeProfileDetails(values).then(response => {
            notification.success({
                message: 'Success',
                description: "You're successfully changed profile details."
            });
            props.history.push('/edit-profile');
        }).catch(error => {
            notification.error({
                message: 'Error',
                description: error.response.data.message
            });
        });
    }

    return (
        <Card title="Personal Details">
            <Form {...layout} ref={props.formRef} onFinish={onFinish}
                initialValues={{ email: email, fullname: full_name, bankAccount: bank_account }}>
                <Form.Item
                    label="E-mail"
                    name="email"
                >
                    <Input disabled />
                </Form.Item>

                <Form.Item
                    label="Full Name"
                    name="fullname"
                >
                    <Input disabled />
                </Form.Item>

                <Form.Item
                    label="Bank Account"
                    name="bankAccount"
                >
                    <Input />
                </Form.Item>

                <Form.Item {...tailLayout}>
                    <Button type="primary" htmlType="submit">Submit</Button>
                </Form.Item>
            </Form>
        </Card>
    );
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