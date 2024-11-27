package datamartapp.controllers.mvcControllers;

import datamartapp.controllers.restControllers.DatasetsController;
import datamartapp.dto.dataset.app.DatasetDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@Controller
@RequestMapping("/data-mart/datasets")
@RequiredArgsConstructor
public class DatasetsControllerMvc {

    private final DatasetsController datasetsController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getDatasetsPage(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                     @RequestParam(value = "sort", defaultValue = "ASC") String sort,
                                     Model model) {
        List<DatasetDtoResponse> datasets = datasetsController.getDatasets(pageNum, sort);
        model.addAttribute("datasetsList", datasets);
        return "datasets";
    }

    @GetMapping("/cr")
    @ResponseStatus(HttpStatus.OK)
    public String getCreateDatasetPage() {
        return "datasets-create";
    }
}
