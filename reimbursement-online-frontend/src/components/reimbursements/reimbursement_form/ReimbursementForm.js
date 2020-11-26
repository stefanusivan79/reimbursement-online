import { ExclamationCircleOutlined, UploadOutlined } from '@ant-design/icons';
import { Button, Card, DatePicker, Form, Input, Modal, notification, Upload } from 'antd';
import React, { Component } from 'react';
import ReimbursementService from '../../../services/ReimbursementService';

const { confirm } = Modal;

export class ReimbursementForm extends Component {

  formRef = React.createRef();

  handleMenu = () => {
    this.props.handleSelectedMenu('reimbursement-form');
  }

  componentDidMount() {
    this.handleMenu();
  }

  render() {

    const normFile = (e) => {
      console.log('Upload event:', e);

      if (Array.isArray(e)) {
        return e;
      }

      return e && e.fileList;
    };

    const onFinish = (values) => {

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

    const showConfirm = (values) => {
      confirm({
        title: 'Reimbursement Form',
        icon: <ExclamationCircleOutlined />,
        content: 'Do you want to submit?',
        onCancel() {
        },
        onOk: () => {
          onFinish(values);
        }
      })
    }

    return (
      <Card title="Reimbursement Form">
        <Form {...layout} ref={this.formRef} onFinish={showConfirm} name="reimburse_form" initialValues={{
          ['attachment']: []
        }}>
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

          <Form.Item
            label="Receipt"
            name="attachment"
            valuePropName="fileList"
            getValueFromEvent={normFile}
          >
            <Upload
              name="logo"
              listType="picture-card"
              customRequest={requestUpload}
              onChange={(info) => {
                // set url
                let fileList = [...info.fileList];
                fileList.forEach(file => {
                  if (file.response) {
                    file.url = file.response.url;
                  }
                });
              }}
              showUploadList={{
                showDownloadIcon: true
              }}
              onDownload={file => {
                const body = {
                  name_original: file.response.name_original,
                  file_name: file.response.file_name,
                  bucket: file.response.bucket
                }

                ReimbursementService.downloadReimbursementAttachment(body)
                  .then(response => {
                    const url = window.URL.createObjectURL(new Blob([response.data]));
                    const link = document.createElement('a');
                    link.href = url;
                    link.setAttribute('download', body.name_original); //or any other extension
                    document.body.appendChild(link);
                    link.click();
                  })
              }}
            >
              <Button>
                <UploadOutlined /> Click to upload
              </Button>

            </Upload>
          </Form.Item>

          <Form.Item {...tailLayout}>
            <Button type="primary" htmlType="submit">Submit</Button>
          </Form.Item>
        </Form>
      </Card>
    );
  }

}

const requestUpload = (componentsData) => {
  let formData = new FormData();
  formData.append('file', componentsData.file);
  formData.append('type', 'file');

  ReimbursementService.uploadReimbursementAttachment(formData)
    .then(response => {
      componentsData.onSuccess(response.data);
    });
}

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
