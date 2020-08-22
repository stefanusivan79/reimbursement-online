import { Space } from 'antd';
import React, { Component } from 'react';
import { EditPassword } from './EditPassword';
import { EditPersonalDetail } from './EditPersonalDetails';
import './EditProfile.css';
import EmployeeService from './../../../services/EmployeeService';

class EditProfile extends Component {

    formRef = React.createRef();

    constructor(props) {
        super(props)

        this.state = {
            dataEmployee: {}
        }
    }


    componentDidMount() {
        EmployeeService.getProfileUser().then(response => {
            this.setState({
                dataEmployee: response.data
            });
            this.formRef.current.resetFields();
        });

    }

    render() {
        return (
            <Space {...layoutSpace}>
                <EditPersonalDetail dataEmployee={this.state.dataEmployee} formRef={this.formRef} {...this.props} />
                <EditPassword {...this.props} />
            </Space>
        )
    }
}

const layoutSpace = {
    direction: 'vertical',
    style: {
        width: '100%'
    },
    size: 'large'
}

export default EditProfile
