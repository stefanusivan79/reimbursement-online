import { instance } from '../utils/AxiosInterceptor';

const DashboardService = {
  getDashboardStatistic() {
    return instance.get('/dashboard/statistic');
  },
}

export default DashboardService
