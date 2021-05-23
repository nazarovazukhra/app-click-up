package uz.pdp.appclickup.service;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.Project;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ProjectDto;
import uz.pdp.appclickup.repository.ProjectRepository;
import uz.pdp.appclickup.repository.SpaceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    final ProjectRepository projectRepository;
    final SpaceRepository spaceRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, SpaceRepository spaceRepository) {
        this.projectRepository = projectRepository;
        this.spaceRepository = spaceRepository;
    }

    @Override
    public ApiResponse addProject(ProjectDto projectDto) {

        boolean exists = projectRepository.existsByNameAndSpaceId(
                projectDto.getName(), spaceRepository.findById(projectDto.getSpaceId()).
                        orElseThrow(() -> new ResourceNotFoundException("space not found")));

        if (exists)
            return new ApiResponse("Such project already exists in this space", false);

        Project project = new Project();
        project.setName(project.getName());
        project.setColor(projectDto.getColor());
        project.setSpaceId(spaceRepository.findById(projectDto.getSpaceId()).orElseThrow(() -> new ResourceNotFoundException("space not found")));

        projectRepository.save(project);
        return new ApiResponse("Project added", true);
    }

    @Override
    public ApiResponse editProject(UUID id, ProjectDto projectDto) {

        Optional<Project> optionalProject = projectRepository.findById(id);
        if (!optionalProject.isPresent())
            return new ApiResponse("Such project not found ", false);

        Project editingProject = optionalProject.get();

        boolean exists = projectRepository.existsByNameAndSpaceId(
                projectDto.getName(), spaceRepository.findById(projectDto.getSpaceId()).
                        orElseThrow(() -> new ResourceNotFoundException("space not found")));

        if (exists)
            return new ApiResponse("Such project already exists in this space", false);

        editingProject.setName(projectDto.getName());
        editingProject.setColor(projectDto.getColor());
        projectRepository.save(editingProject);
        return new ApiResponse("Project edited", true);
    }

    @Override
    public ApiResponse deleteProject(UUID id) {

        boolean exists = projectRepository.existsById(id);
        if (!exists)
            return new ApiResponse("Such project not found ", false);

        projectRepository.deleteById(id);
        return new ApiResponse("Project deleted ", true);
    }

    @Override
    public List<Project> getAllProjects() {

        return projectRepository.findAll();
    }
}
