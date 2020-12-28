import { instance } from '../utils/AxiosInterceptor';

const ReportService = {

  getReport(body) {
    return instance.post('/reimbursement/report', body, {
      responseType: 'blob'
    });
  },

  getListReport() {
    return instance.get('/reimbursement/list-report');
  },

}

export default ReportService
