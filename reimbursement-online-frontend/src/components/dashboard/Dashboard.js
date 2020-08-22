import { Avatar, Button, Drawer, Layout, Menu } from 'antd';
import React, { Component } from 'react';
import { Link, Route, Switch } from 'react-router-dom';
import { ACCESS_TOKEN, REFRESH_TOKEN } from '../../utils/Constants';
import routes from "../../utils/Routes";
import AppHeader from '../common/app_header/AppHeader';
import { ReimbursementForm } from '../reimbursements/reimbursement_form/ReimbursementForm';
import ReimbursementList from '../reimbursements/reimbursement_list/ReimbursementList';
import EditProfile from '../user/edit-profile/EditProfile';
import ReimbursementListAdmin from './../reimbursements/reimbursement_list_admin/ReimbursementListAdmin';
import './Dashboard.css';

const { Content, Sider } = Layout;

class Dashboard extends Component {

    constructor(props) {
        super(props)

        this.state = {
            visibleProfile: false,
            keyMenu: '',
            avatarName: '',
            name: localStorage.getItem('name')
        }
    }

    componentDidMount() {
        this.getAvatarName();
    }

    getAvatarName = () => {
        const splitName = this.state.name.split(' ');
        if (splitName.length === 1) {
            this.setState({
                avatarName: splitName[0][0].toUpperCase()
            })
        }
        else {
            this.setState({
                avatarName: `${splitName[0][0]}${splitName[1][0]}`.toUpperCase()
            })
        }
    }

    showDrawer = () => {
        this.setState({
            visibleProfile: true,
        });
    };

    closeDrawer = () => {
        this.setState({
            visibleProfile: false,
        });
    };

    signOut = () => {
        localStorage.removeItem(ACCESS_TOKEN);
        localStorage.removeItem(REFRESH_TOKEN);
        this.props.history.push("/login");
    }

    handleSelectedMenu = (keyMenu) => {
        this.setState({
            keyMenu: keyMenu
        })
    }

    render() {
        const routesUser = localStorage.getItem('isUser') ? routes.user : [];
        const routesAdmin = localStorage.getItem('isAdmin') ? routes.admin : [];
        return (
            <div>
                <Layout style={{ minHeight: "100vh" }}>
                    <AppHeader showDrawer={this.showDrawer} avatarName={this.state.avatarName} name={this.state.name} />

                    <Layout className="main-wrapper">
                        <Sider width={256} className="site-layout-background"
                            style={{ padding: 24, borderRight: '1px solid #f0f0f0', backgroundColor: '#fff' }}
                            role="menu">
                            <Menu
                                mode="inline"
                                selectedKeys={[this.state.keyMenu]}
                                style={{ height: '100%', borderRight: 0 }}
                            >
                                {
                                    routesUser.map((route) =>
                                        <Menu.ItemGroup title={route.title} key={route.title}>
                                            {
                                                route.data.map(list =>
                                                    <Menu.Item key={list.key}>
                                                        <Link to={list.url}>{list.label}</Link>
                                                    </Menu.Item>
                                                )
                                            }
                                        </Menu.ItemGroup>
                                    )
                                }

                                {
                                    routesAdmin.map((route) =>
                                        <Menu.ItemGroup title={route.title} key={route.title}>
                                            {
                                                route.data.map(list =>
                                                    <Menu.Item key={list.key}>
                                                        <Link to={list.url}>{list.label}</Link>
                                                    </Menu.Item>
                                                )
                                            }
                                        </Menu.ItemGroup>
                                    )
                                }
                            </Menu>
                        </Sider>
                        <Layout>
                            <Layout style={{ background: '#fff' }}>
                                <Content className="content" style={{ marginTop: 20, marginBottom: 20 }}>
                                    <Switch>
                                        <Route exact path={'/edit-profile'} component={EditProfile} />
                                        <Route exact path={'/reimbursement-list'} render={() => <ReimbursementList
                                            handleSelectedMenu={this.handleSelectedMenu} />} />
                                        <Route exact path={'/reimbursement-form'} render={() => <ReimbursementForm
                                            handleSelectedMenu={this.handleSelectedMenu} />} />
                                        <Route exact path={'/reimbursement-list-admin'} render={() => <ReimbursementListAdmin
                                            handleSelectedMenu={this.handleSelectedMenu} />} />
                                    </Switch>
                                </Content>
                            </Layout>
                        </Layout>
                    </Layout>
                </Layout>
                <Drawer
                    title="Profile"
                    placement={"right"}
                    closable={false}
                    onClose={this.closeDrawer}
                    visible={this.state.visibleProfile}
                >
                    <div>
                        <div style={{ width: '100%' }}>
                            <Avatar style={{ display: 'table', margin: '0 auto' }} size={64}>
                                {this.state.avatarName}
                            </Avatar>
                        </div>
                        <br />
                        <div style={{ width: '100%' }}>
                            <span style={{ display: 'table', margin: '0 auto' }}>
                                <Link to={'/edit-profile'} onClick={this.closeDrawer}>Edit Profile</Link>
                            </span>
                        </div>
                        <br />
                        <div style={{ width: '100%' }}>
                            <Button style={{ display: 'table', margin: '0 auto' }} onClick={this.signOut}>Sign
                                Out</Button>
                        </div>
                    </div>
                </Drawer>
            </div>
        )
    }
}

export default Dashboard
