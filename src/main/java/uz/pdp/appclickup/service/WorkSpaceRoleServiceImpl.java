package uz.pdp.appclickup.service;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.WorkSpace;
import uz.pdp.appclickup.entity.WorkSpacePermission;
import uz.pdp.appclickup.entity.WorkSpaceRole;
import uz.pdp.appclickup.entity.enums.WorkSpacePermissionName;
import uz.pdp.appclickup.entity.enums.WorkSpaceRoleName;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.WorkSpaceRoleDto;
import uz.pdp.appclickup.repository.WorkSpacePermissionRepository;
import uz.pdp.appclickup.repository.WorkSpaceRepository;
import uz.pdp.appclickup.repository.WorkSpaceRoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkSpaceRoleServiceImpl implements WorkSpaceRoleService {

    final WorkSpaceRoleRepository workSpaceRoleRepository;
    final WorkSpaceRepository workSpaceRepository;
    final WorkSpacePermissionRepository workSpacePermissionRepository;

    public WorkSpaceRoleServiceImpl(WorkSpaceRoleRepository workSpaceRoleRepository, WorkSpaceRepository workSpaceRepository, WorkSpacePermissionRepository workSpacePermissionRepository) {
        this.workSpaceRoleRepository = workSpaceRoleRepository;
        this.workSpaceRepository = workSpaceRepository;
        this.workSpacePermissionRepository = workSpacePermissionRepository;
    }

    @Override
    public ApiResponse addWorkSpaceRole(WorkSpaceRoleDto workSpaceRoleDto) {

        Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(workSpaceRoleDto.getWorkSpaceId());
        if (!optionalWorkSpace.isPresent())
            return new ApiResponse("Such workSpace not found", false);

        WorkSpace workSpace = optionalWorkSpace.get();

        boolean exists = workSpaceRoleRepository.existsByNameAndWorkSpaceId(workSpaceRoleDto.getRoleName(), workSpace);
        if (exists)
            return new ApiResponse("Such role already exists in this workSpace", false);


        WorkSpaceRole workSpaceRole = new WorkSpaceRole();
        workSpaceRole.setWorkSpaceId(workSpace);
        workSpaceRole.setName(workSpaceRoleDto.getRoleName());
        workSpaceRole.setExtendsRole(WorkSpaceRoleName.valueOf(workSpaceRoleDto.getExtendsRole()));

        WorkSpaceRole otherRole = workSpaceRoleRepository.save(workSpaceRole);

        WorkSpacePermissionName[] workSpacePermissionNames = WorkSpacePermissionName.values();
        List<WorkSpacePermission> workSpacePermissionList = new ArrayList<>();
        for (WorkSpacePermissionName workSpacePermissionName : workSpacePermissionNames) {

            if (workSpaceRoleDto.getExtendsRole().equals("ROLE_ADMIN")) {
                if (workSpacePermissionName.getWorkSpaceRoleNames().contains(WorkSpaceRoleName.ROLE_ADMIN)) {
                    workSpacePermissionList.add(new WorkSpacePermission(otherRole, workSpacePermissionName));
                }
            }
            if (workSpaceRoleDto.getExtendsRole().equals("ROLE_MEMBER")) {

                if (workSpacePermissionName.getWorkSpaceRoleNames().contains(WorkSpaceRoleName.ROLE_MEMBER)) {
                    workSpacePermissionList.add(new WorkSpacePermission(otherRole, workSpacePermissionName));
                }
            }
            if (workSpaceRoleDto.getExtendsRole().equals("ROLE_GUEST")) {

                if (workSpacePermissionName.getWorkSpaceRoleNames().contains(WorkSpaceRoleName.ROLE_GUEST)) {
                    workSpacePermissionList.add(new WorkSpacePermission(otherRole, workSpacePermissionName));
                }

            }
        }
        workSpacePermissionRepository.saveAll(workSpacePermissionList);

        return new ApiResponse("WorkSpaceRole added", true);

    }

    @Override
    public List<WorkSpaceRole> getAll() {
        return workSpaceRoleRepository.findAll();
    }

    @Override
    public WorkSpaceRole getOneById(UUID id) {

        Optional<WorkSpaceRole> optionalWorkSpaceRole = workSpaceRoleRepository.findById(id);
        return optionalWorkSpaceRole.orElse(null);
    }

    @Override
    public ApiResponse deleteById(UUID workSpaceId, UUID workSpaceRoleId) {

        Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(workSpaceId);
        if (!optionalWorkSpace.isPresent())
            return new ApiResponse("Such workSpace not found", false);

        WorkSpace workSpace = optionalWorkSpace.get();

        workSpaceRoleRepository.deleteByIdAndWorkSpaceId(workSpaceRoleId, workSpace);
        return new ApiResponse("WorkSpaceRole deleted with workSpaceId", true);
    }

    @Override
    public List<WorkSpaceRole> getAllWorkSpaceRoleByWorkSpaceId(UUID workSpaceId) {

        Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(workSpaceId);
        if (!optionalWorkSpace.isPresent())
            return null;
        WorkSpace workSpace = optionalWorkSpace.get();

        return workSpaceRoleRepository.findAllByWorkSpaceId(workSpace);
    }

    @Override
    public WorkSpaceRole getOneWorkSpaceRoleByIdAndWorkSpaceId(UUID workSpaceId, UUID workSpaceRoleId) {

        Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(workSpaceId);
        if (!optionalWorkSpace.isPresent())
            return null;
        WorkSpace workSpace = optionalWorkSpace.get();

        Optional<WorkSpaceRole> optionalWorkSpaceRole = workSpaceRoleRepository.findByIdAndWorkSpaceId(workSpaceRoleId, workSpace);
        return optionalWorkSpaceRole.orElse(null);

    }
}