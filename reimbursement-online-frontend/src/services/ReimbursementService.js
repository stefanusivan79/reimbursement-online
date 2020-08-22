import { instance } from '../utils/AxiosInterceptor';

const ReimbursementService = {
    getReimbursementList(config) {
        return instance.get('/reimbursement/employee', config);
    },

    submitReimbursement(body) {
        return instance.post('/reimbursement', body);
    },

    getAllReimbursementList(config) {
        return instance.get('/reimbursement/all', config);
    },

    rejectReimbursement(config) {
        return instance.put('/reimbursement/reject', null, config);
    },

    acceptReimbursement(config) {
        return instance.put('/reimbursement/accept', null, config);
    },

    completeReimbursement(config) {
        return instance.put('/reimbursement/complete', null, config);
    }
}

export default ReimbursementService;
