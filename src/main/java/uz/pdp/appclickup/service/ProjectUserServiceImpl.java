package uz.pdp.appclickup.service;

import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.*;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ProjectUserDto;
import uz.pdp.appclickup.repository.ProjectRepository;
import uz.pdp.appclickup.repository.ProjectUserRepository;
import uz.pdp.appclickup.repository.UserRepository;
import uz.pdp.appclickup.repository.WorkSpacePermissionRepository;

import java.util.List;
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

        ProjectUser projectUser = new ProjectUser();
        projectUser.setUserId(userRepository.getOne(projectUserDto.getUserId()));
        projectUser.setProjectId(projectRepository.getOne(projectUserDto.getProjectId()));
        projectUser.setTaskPermission(workSpacePermissionRepository.getOne(projectUserDto.getTaskPermission()).getPermission());
        projectUserRepository.save(projectUser);
        return new ApiResponse("", true);
    }

    @Override
    public ApiResponse editProjectUser(ProjectUserDto projectUserDto) {

        Optional<ProjectUser> optionalProjectUser = projectUserRepository.findByProjectIdAndUserId(projectUserDto.getProjectId(), projectUserDto.getUserId());
        if (!optionalProjectUser.isPresent())
            return new ApiResponse("In this user does not have such project ", false);

        ProjectUser editingProjectUser = optionalProjectUser.get();

        boolean exists = projectUserRepository.existsByProjectIdAndUserIdAndIdNot(projectUserDto.getProjectId(), projectUserDto.getUserId());
        if (exists)
            return new ApiResponse("In this project already has such user", false);

        editingProjectUser.setUserId(userRepository.getOne(projectUserDto.getUserId()));
        editingProjectUser.setProjectId(projectRepository.getOne(projectUserDto.getProjectId()));
        editingProjectUser.setTaskPermission(workSpacePermissionRepository.getOne(projectUserDto.getTaskPermission()).getPermission());
        projectUserRepository.save(editingProjectUser);

        return new ApiResponse("ProjectUser edited", true);

    }

    @Override
    public ApiResponse deleteProjectUser(UUID id) {

        boolean exists = projectUserRepository.existsById(id);
        if (!exists)
            return new ApiResponse("Not found", false);

        projectUserRepository.deleteById(id);
        return new ApiResponse("User deleted from this project", true);
    }

    @Override
    public List<ProjectUser> getAll(UUID workSpaceId, UUID ownerId) {

        return projectUserRepository.getProjectUsers(workSpaceId, ownerId);
    }


    @Override
    public ProjectUser getOne(UUID workSpaceId, UUID projectUserId) {

        return projectUserRepository.getOneProjectUser(workSpaceId, projectUserId);
    }
}
