import React, { Component } from 'react';
import { Card, Col, Row, Statistic } from 'antd';
import DashboardService from '../../services/DashboardService';

class DashboardStatistic extends Component {

  state = {
    dashboardStatistic: {}
  }

  componentDidMount() {
    DashboardService.getDashboardStatistic().then(response => {
      this.setState({
        dashboardStatistic: response.data
      })
    });

    this.handleMenu();
  }

  handleMenu = () => {
    this.props.handleSelectedMenu('statistic');
  }

  render() {
    const { submitted, in_progress, rejected, completed } = this.state.dashboardStatistic;
    return (
      <div>
        <Card title='This Month'>
          <Row gutter={16}>
            <Col span={4}>
              <Card>
                <Statistic title='Submitted' value={submitted} valueStyle={{ color: '#2cc6ff' }} />
              </Card>
            </Col>
            <Col span={4}>
              <Card>
                <Statistic title='In Progress' value={in_progress} valueStyle={{ color: '#8238db' }} />
              </Card>
            </Col>
            <Col span={4}>
              <Card>
                <Statistic title='Completed' value={completed} valueStyle={{ color: '#3fc930' }} />
              </Card>
            </Col>
            <Col span={4}>
              <Card>
                <Statistic title='Rejected' value={rejected} valueStyle={{ color: '#ff0000' }} />
              </Card>
            </Col>
          </Row>
        </Card>
      </div>
    )
  }
}

export default DashboardStatistic
