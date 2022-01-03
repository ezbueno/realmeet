package br.com.sw2you.realmeet.report.resolver;

import br.com.sw2you.realmeet.report.enumeration.ReportHandlerType;
import br.com.sw2you.realmeet.report.handler.AbstractReportHandler;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class ReportHandlerResolver {
    private final Set<AbstractReportHandler> reportHandlerSet;
    private final Map<ReportHandlerType, AbstractReportHandler> reportHandlerMap;

    public ReportHandlerResolver(Set<AbstractReportHandler> reportHandlerSet) {
        this.reportHandlerSet = reportHandlerSet;
        this.reportHandlerMap = new ConcurrentHashMap<>();
    }

    public AbstractReportHandler resolveReportHandler(ReportHandlerType reportHandlerType) {
        return this.reportHandlerMap.computeIfAbsent(reportHandlerType, this::findReportHandler);
    }

    private AbstractReportHandler findReportHandler(ReportHandlerType reportHandlerType) {
        return this.reportHandlerSet.stream()
            .filter(reportHandler -> reportHandlerType == reportHandler.getReportHandlerType())
            .findFirst()
            .orElseThrow(
                () ->
                    new UnsupportedOperationException(
                        "Report handler not implemented for type: " + reportHandlerType.name()
                    )
            );
    }
}
