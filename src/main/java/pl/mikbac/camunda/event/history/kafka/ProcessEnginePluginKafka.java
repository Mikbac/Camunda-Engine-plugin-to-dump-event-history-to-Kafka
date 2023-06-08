package pl.mikbac.camunda.event.history.kafka;

import lombok.Setter;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.history.handler.CompositeDbHistoryEventHandler;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;

/**
 * Created by MikBac on 07.06.2023
 */
public class ProcessEnginePluginKafka implements ProcessEnginePlugin {

    @Setter
    private String kafkaBrokers;

    @Setter
    private String kafkaTopic;

    @Setter
    private String clientId;

    @Override
    public void preInit(final ProcessEngineConfigurationImpl processEngineConfiguration) {
        final HistoryEventHandler eventHandler = new HistoryEventHandlerKafka(clientId, kafkaBrokers, kafkaTopic);
        processEngineConfiguration.setHistoryEventHandler(new CompositeDbHistoryEventHandler(eventHandler));
    }

    @Override
    public void postInit(final ProcessEngineConfigurationImpl processEngineConfiguration) {

    }

    @Override
    public void postProcessEngineBuild(final ProcessEngine processEngine) {

    }
}
