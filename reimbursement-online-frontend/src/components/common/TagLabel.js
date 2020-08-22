import { Tag } from "antd";
import React, { Component } from 'react';
import { STATUS__REIMBURSEMENT_COMPLETED, STATUS__REIMBURSEMENT_ON_PROGRESS, STATUS__REIMBURSEMENT_SUBMITTED } from '../../utils/Constants';

class TagLabel extends Component {

    state = {
        color: '',
        status: this.props.status
    }

    getColor = (status) => {
        let color;
        switch (status) {
            case STATUS__REIMBURSEMENT_SUBMITTED:
                color = 'geekblue';
                break;
            case STATUS__REIMBURSEMENT_ON_PROGRESS:
                color = 'cyan';
                break;
            case STATUS__REIMBURSEMENT_COMPLETED:
                color = 'green';
                break;
            default:
                color = 'volcano'
                break;
        }

        return color;
    }

    componentDidMount() {
        const { status } = this.state;

        this.setState({
            color: this.getColor(status)
        });
    }

    render() {
        const { status, color } = this.state;

        return (
            <Tag color={color}>
                {status === STATUS__REIMBURSEMENT_ON_PROGRESS ? 'ON PROGRESS' : status}
            </Tag>
        )
    }
}

export default TagLabel;