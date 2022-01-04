package br.com.sw2you.realmeet.model;

import br.com.sw2you.realmeet.report.model.GeneratedReport;
import br.com.sw2you.realmeet.service.ReportDispatcherService;

public class DiskReportDispatcher extends ReportDispatcherService {
    public DiskReportDispatcher() {
        super(null, null);
    }

    @Override
    public void dispatch(GeneratedReport generatedReport) {
        super.dispatch(generatedReport);
    }
}
