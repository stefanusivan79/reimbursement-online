const routes = {
    user: [
        {
            title: 'Reimbursement',
            data: [
                {
                    label: 'Reimbursement Form',
                    key: 'reimbursement-form',
                    url: '/reimbursement-form'
                },
                {
                    label: 'Reimbursement List',
                    key: 'reimbursement-list',
                    url: '/reimbursement-list'
                }
            ]
        }
    ],
    admin: [
        {
            title: 'Reimbursement',
            data: [
                {
                    label: 'Reimbursement List',
                    key: 'reimbursement-list-admin',
                    url: '/reimbursement-list-admin'
                }
            ]
        },
        {
            title: 'Report',
            data: [
                {
                    label: 'Report',
                    key: 'report',
                    url: '/report'
                }
            ]
        }
    ]
}

export default routes;
