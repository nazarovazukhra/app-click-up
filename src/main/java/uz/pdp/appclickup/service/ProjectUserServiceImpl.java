package uz.pdp.appclickup.service;

import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.*;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ProjectUserDto;
import uz.pdp.appclickup.repository.ProjectRepository;
import uz.pdp.appclickup.repository.ProjectUserRepository;
import uz.pdp.appclickup.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;


@Service
public class ProjectUserServiceImpl implements ProjectUserService {

    final ProjectUserRepository projectUserRepository;
    final UserRepository userRepository;
    final ProjectRepository projectRepository;
    final WorkSpacePermissionRepository workSpacePermissionRepository;

    public ProjectUserServiceImpl(ProjectUserRepository projectUserRepository, UserRepository userRepository, ProjectRepository projectRepository, WorkSpacePermissionRepository workSpacePermissionRepository) {

        this.projectUserRepository = projectUserRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.workSpacePermissionRepository = workSpacePermissionRepository;
    }

    @Override
    public ApiResponse addProjectUser(ProjectUserDto projectUserDto) {

        Optional<Project> optionalProject = projectRepository.findById(projectUserDto.getProjectId());
        if (!optionalProject.isPresent())
            return new ApiResponse("Such project not found", false);
        Project project = optionalProject.get();

        Optional<User> optionalUser = userRepository.findById(projectUserDto.getUserId());
        if (!optionalUser.isPresent())
            return new ApiResponse("Such project not found", false);
        User user = optionalUser.get();

        boolean exists = projectUserRepository.existsByProjectIdAndUserId(project, user);
        if (exists)
            return new ApiResponse("In this project already has such user", false);

        projectUserRepository.save(new ProjectUser(project, user, projectUserDto.getTaskPermission()));
        return new ApiResponse("", true);
    }

    @Override
    public ApiResponse editProjectUser(UUID id, ProjectUserDto projectUserDto) {

        Optional<ProjectUser> optionalProjectUser = projectUserRepository.findById(id);
        if (!optionalProjectUser.isPresent())
            return new ApiResponse("Not found", false);
        ProjectUser editingProjectUser = optionalProjectUser.get();


        Optional<User> optionalUser = userRepository.findById(projectUserDto.getUserId());
        if (!optionalUser.isPresent())
            return new ApiResponse("Such project not found", false);
        User user = optionalUser.get();

        boolean exists = projectUserRepository.existsByProjectIdAndUserId
                (projectRepository.findById(projectUserDto.getProjectId()).orElseThrow(null), user);
        if (exists)
            return new ApiResponse("In this project already has such user", false);

        editingProjectUser.setUserId(user);
        return new ApiResponse("", true);

    }

    @Override
    public ApiResponse deleteProjectUser(UUID id) {

        boolean exists = projectUserRepository.existsById(id);
        if (!exists)
            return new ApiResponse("Not found", false);

        projectUserRepository.deleteById(id);
        return new ApiResponse("User deleted from this project", true);
    }
}
