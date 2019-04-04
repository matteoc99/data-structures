package data_structures.nn.network_gui;

/**
 * @author Maximilian Estfeller
 * @since 23.05.17
 */
interface NetworkGUIComponent {

    enum NetworkGUIComponentType {
        CONNECTION,
        NETWORK_CONTAINER,
        NETWORK_PANEL,
        NEURON
    }

    Object getEquivalent();

    void setEquivalent(Object equivalent);

    void toggleDeveloperMode();

    void refresh();

    void reset();

    NetworkGUIComponentType getNetworkGUIComponentType();
}
