import { Loading3QuartersOutlined } from '@ant-design/icons';
import { Spin } from 'antd';
import React from 'react';

export const LoadingIndicator = () => {
    const antIcon = <Loading3QuartersOutlined spin style={{ fontSize: 30 }} />
    return (
        <Spin indicator={antIcon} style={{ display: 'block', textAlign: 'center', marginTop: 30 }} />
    )
}
