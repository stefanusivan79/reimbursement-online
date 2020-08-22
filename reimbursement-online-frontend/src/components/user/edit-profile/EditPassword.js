import { Button, Card, Form, Input, notification } from 'antd';
import React from 'react';
import EmployeeService from './../../../services/EmployeeService';

export const EditPassword = (props) => {

    const onFinish = (values) => {
        EmployeeService.changePassword(values).then(response => {
            notification.success({
                message: 'Success',
                description: "You're successfully changed password."
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
        <Card title="Change Password">
            <Form {...layout} onFinish={onFinish}>
                <Form.Item
                    label="Current Password"
                    name="currentPassword"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your password!'
                        }
                    ]}
                >
                    <Input.Password />
                </Form.Item>

                <Form.Item
                    label="New Password"
                    name="newPassword"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your password!'
                        }
                    ]}
                >
                    <Input.Password />
                </Form.Item>

                <Form.Item
                    label="Confirm New Password"
                    name="confirmPassword"
                    dependencies={['newPassword']}
                    hasFeedback
                    rules={[
                        {
                            required: true,
                            message: 'Please confirm your password!'
                        },
                        ({getFieldValue}) => ({
                            validator(rule, value) {
                                if(!value|| getFieldValue('newPassword') === value) {
                                    return Promise.resolve();
                                }

                                return Promise.reject('The two passwords that you entered do not match!');
                            }
                        })
                    ]}
                >
                    <Input.Password />
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