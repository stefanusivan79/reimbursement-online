import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Button, Form, Input, notification } from 'antd';
import React, { Component } from 'react';
import { ACCESS_TOKEN, REFRESH_TOKEN } from '../../../utils/Constants';
import AuthenticationService from './../../../services/AuthenticationService';
import './Login.css';

class Login extends Component {

    handleLogin() {
        notification.success({
            message: 'Reimbursement App',
            description: "You're successfully logged in.",
        });
        this.props.history.push("/");
    }

    onFinish = (values) => {
        AuthenticationService.loginUser(values).then(response => {
            const { data } = response;
            localStorage.removeItem('isUser');
            localStorage.removeItem('isAdmin');
            localStorage.removeItem('name');
            localStorage.setItem(ACCESS_TOKEN, data.access_token);
            localStorage.setItem(REFRESH_TOKEN, data.refresh_token);
            localStorage.setItem('name', data.name);
            data.roles.forEach(role => {
                if (role === 'USER') {
                    localStorage.setItem('isUser', true);
                } else if (role === 'ADMIN') {
                    localStorage.setItem('isAdmin', true);
                }
            })
            this.setState({
                isAuthenticated: true
            })
            this.handleLogin();
        }).catch(error => {
            if (error.status === 401) {
                notification.error({
                    message: 'Error',
                    description: 'Your Username or Password is incorrect. Please try again!'
                });
            } else {
                notification.error({
                    message: 'Error',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });
            }
        });
    };

    render() {
        return (
            <div className="login-container">
                <h1 className="page-title">Member Login</h1>
                <div className="login-content">
                    <Form onFinish={this.onFinish}>

                        <Form.Item
                            name="username"
                            rules={[{ required: true, message: 'Please input your username!' }]}
                        >
                            <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Username" />
                        </Form.Item>

                        <Form.Item
                            name="password"
                            rules={[{ required: true, message: 'Please input your password!' }]}
                        >
                            <Input.Password
                                prefix={<LockOutlined className="site-form-item-icon" />}
                                placeholder="Password"
                            />
                        </Form.Item>

                        <Form.Item>
                            <Button type="primary" htmlType="submit" size="large"
                                className="login-form-button">Submit</Button>
                        </Form.Item>
                    </Form>
                </div>
            </div>
        )
    }
}

export default Login
