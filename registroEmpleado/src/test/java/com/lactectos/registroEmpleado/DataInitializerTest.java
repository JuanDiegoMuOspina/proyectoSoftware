package com.lactectos.registroEmpleado;

import com.lactectos.registroEmpleado.domain.model.entity.Rol;
import com.lactectos.registroEmpleado.domain.util.DataInitializer;
import com.lactectos.registroEmpleado.infraestructure.repository.RolRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataInitializerTest {
    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private DataInitializer dataInitializer;

    @Test
    public void testRun() throws Exception {
        when(rolRepository.findByNombreRol(anyString())).thenReturn(null);

        dataInitializer.run();

        verify(rolRepository, times(1)).findByNombreRol("admin");
        verify(rolRepository, times(1)).save(argThat(rol -> rol.getNombreRol().equals("admin")));

        verify(rolRepository, times(1)).findByNombreRol("vendedor");
        verify(rolRepository, times(1)).save(argThat(rol -> rol.getNombreRol().equals("vendedor")));

        verify(rolRepository, times(1)).findByNombreRol("creador");
        verify(rolRepository, times(1)).save(argThat(rol -> rol.getNombreRol().equals("creador")));

        verify(rolRepository, times(1)).findByNombreRol("No defined");
        verify(rolRepository, times(1)).save(argThat(rol -> rol.getNombreRol().equals("No defined")));
    }
}