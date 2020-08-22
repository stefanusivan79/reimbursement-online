import { Avatar, Layout } from 'antd';
import React, { Component } from 'react';
import logo from '../../../assets/images/logo.png';
import './AppHeader.css';

const { Header } = Layout;
class AppHeader extends Component {
    render() {
        return (
            <Header className="header" style={{ lineHeight: '63px ' }}>
                <div className="logo">
                    <img alt="" src={logo} width="80px" />
                </div>
                <div className="rightMenuItem">
                    <Avatar style={{ cursor: 'pointer' }} onClick={this.props.showDrawer}>{this.props.avatarName}</Avatar>
                </div>
            </Header>
        )
    }
}

export default AppHeader
