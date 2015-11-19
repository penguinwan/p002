package groovy.com.jt.givi.core

import com.jt.givi.api.IMasterSetupManager
import com.jt.givi.api.IPiCommunicationManager
import com.jt.givi.api.IStateManager
import com.jt.givi.api.IStorageManager
import com.jt.givi.core.Controller
import com.jt.givi.core.StorageManager
import com.jt.givi.model.Machine
import com.jt.givi.model.Mold
import com.jt.givi.model.Status
import com.jt.givi.model.ValueContainer
import org.junit.Test

import java.text.SimpleDateFormat

import static org.mockito.Mockito.*
import static org.junit.Assert.*

/**
 * Created by superman on 11/19/2015.
 */
class ControllerTest {
    def startupTime = new SimpleDateFormat('yyyy/MM/dd').parse('1977/01/01')
    def machine1 = new Machine(1, new Mold('A', 2), 100, 0, new Status(Status.State.BLACK, startupTime))
    def machine2 = new Machine(2, new Mold('A', 2), 100, 0, new Status(Status.State.BLACK, startupTime))
    def machine3 = new Machine(3, new Mold('A', 2), 100, 0, new Status(Status.State.BLACK, startupTime))
    def machine4 = new Machine(4, new Mold('A', 2), 100, 0, new Status(Status.State.BLACK, startupTime))
    def machine5 = new Machine(5, new Mold('A', 2), 100, 0, new Status(Status.State.BLACK, startupTime))
    def machine6 = new Machine(6, new Mold('A', 2), 100, 0, new Status(Status.State.BLACK, startupTime))
    def machine7 = new Machine(7, new Mold('A', 2), 100, 0, new Status(Status.State.BLACK, startupTime))
    def machine8 = new Machine(8, new Mold('A', 2), 100, 0, new Status(Status.State.BLACK, startupTime))
    def machine9 = new Machine(9, new Mold('A', 2), 100, 0, new Status(Status.State.BLACK, startupTime))
    def machine10 = new Machine(10, new Mold('A', 2), 100, 0, new Status(Status.State.BLACK, startupTime))

    @Test
    void updateMachine_fromBlack_toYellow() {
        IMasterSetupManager masterSetupManager = mock(IMasterSetupManager.class)
        when(masterSetupManager.load()).thenReturn(getMold())

        IStateManager stateManager = mock(IStateManager.class)
        when(stateManager.load()).thenReturn(getMachine())

        IPiCommunicationManager piCommunicationManager = mock(IPiCommunicationManager.class)
        def valueContainer = new ValueContainer(5, 2, Status.State.GREEN)
        when(piCommunicationManager.getValue(5)).thenReturn(valueContainer)

        IStorageManager storeManager = mock(IStorageManager.class)

        def controller = new Controller(masterSetupManager,
                stateManager,
                storeManager,
                piCommunicationManager)
        controller.updateMachine(5)

        // reset datetime
        Status status = controller.getMachineTableModel().getValueAt(4, Machine.Column.STATUS.index)
        assertTrue(status.getLastChanged().getTime() != startupTime.getTime())

        // log state
        verify(storeManager, times(1)).writeLog(machine5, StorageManager.REMARK_STATUS_CHANGED)

        // table model set actual
        int actual = controller.getMachineTableModel().getValueAt(4, Machine.Column.ACTUAL.index)
        assertEquals(4, actual)

        // others unchanged
        assertEquals(0, controller.getMachineTableModel().getValueAt(0, Machine.Column.ACTUAL.index))
        assertEquals(0, controller.getMachineTableModel().getValueAt(1, Machine.Column.ACTUAL.index))
        assertEquals(0, controller.getMachineTableModel().getValueAt(2, Machine.Column.ACTUAL.index))
        assertEquals(0, controller.getMachineTableModel().getValueAt(3, Machine.Column.ACTUAL.index))
        assertEquals(0, controller.getMachineTableModel().getValueAt(5, Machine.Column.ACTUAL.index))
        assertEquals(0, controller.getMachineTableModel().getValueAt(6, Machine.Column.ACTUAL.index))
        assertEquals(0, controller.getMachineTableModel().getValueAt(7, Machine.Column.ACTUAL.index))
        assertEquals(0, controller.getMachineTableModel().getValueAt(8, Machine.Column.ACTUAL.index))
        assertEquals(0, controller.getMachineTableModel().getValueAt(9, Machine.Column.ACTUAL.index))
    }

    List<Mold> getMold() {
        def molds = [
                new Mold('A', 2),
                new Mold('B', 4)
        ]
        return molds
    }

    List<Machine> getMachine() {
        def machines = [
                machine1, machine2, machine3, machine4, machine5, machine6, machine7, machine8, machine9, machine10
        ]
        return machines
    }
}
