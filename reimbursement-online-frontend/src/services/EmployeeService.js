import { instance } from '../utils/AxiosInterceptor';

const EmployeeService = {
    getProfileUser() {
        return instance.get('/employee/profile');
    },

    changePassword(values) {
        const data = {
            current_password: values.currentPassword,
            new_password: values.newPassword
        };
        return instance.put('/employee/edit-password', data);
    },

    changeProfileDetails(values) {
        const data = {
            full_name: values.fullname,
            bank_account: values.bankAccount
        };
        return instance.put('/employee/edit-profile', data);
    }
}

export default EmployeeService;

