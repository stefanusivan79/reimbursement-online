import React, { Component } from 'react';
import ReportService from '../../services/ReportService';
import { Button, Table } from 'antd';

class ReportPerMonth extends Component {

  state = {
    listReport: [],
    loading: true
  }

  columns = [
    {
      title: 'Periode',
      dataIndex: 'periode',
      key: 'periode'
    },
    {
      title: 'Action',
      render: (data) => {
        return <Button onClick={() => this.report(data)}>Download</Button>;
      }
    }
  ]

  componentDidMount() {
    ReportService.getListReport().then(response => {
      this.setState({
        listReport: response.data,
        loading: false
      })
    })

    this.handleMenu();
  }

  report = (body) => {
    ReportService.getReport(body).then(response => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', body.periode); //or any other extension
      document.body.appendChild(link);
      link.click();
    })
  }

  handleMenu = () => {
    this.props.handleSelectedMenu('report');
  }

  render() {
    const { listReport, loading } = this.state;
    return (
      <Table
        title={() => <h2>Report</h2>}
        columns={this.columns}
        dataSource={listReport}
        rowKey={data => data.periode}
        loading={loading}
      />
    )
  }

}

export default ReportPerMonth
