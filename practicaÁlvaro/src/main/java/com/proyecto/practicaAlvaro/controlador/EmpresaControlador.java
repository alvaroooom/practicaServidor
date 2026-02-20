package com.proyecto.practicaAlvaro.controlador;

import com.proyecto.practicaAlvaro.modelo.Empresa;
import com.proyecto.practicaAlvaro.servicio.EmpresaServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/empresas")
public class EmpresaControlador {

    private final EmpresaServicio empresaServicio;

    public EmpresaControlador(EmpresaServicio empresaServicio) {
        this.empresaServicio = empresaServicio;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("empresas", empresaServicio.findAll());
        return "empresas/index";
    }

    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresas/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        empresaServicio.findById(id).ifPresent(e -> {
            model.addAttribute("empresa", e);
        });
        return "empresas/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Empresa empresa) {
        empresaServicio.save(empresa);
        return "redirect:/empresas";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            empresaServicio.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Empresa eliminada correctamente");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "No se puede eliminar la empresa porque tiene prácticas asociadas");
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        return "redirect:/empresas";
    }
}

