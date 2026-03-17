package principal.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import principal.Models.Orden;
import principal.Services.OrdenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ordenes")
public class OrdenController {
    @Autowired
    private OrdenService ordenService;
    @PostMapping
    public Orden crear(@RequestBody Orden orden){
        return ordenService.registrarOrden(orden);
    }
    @GetMapping
    public List<Orden> getTodos(){
        return ordenService.listarOrdenes();
    }
}
