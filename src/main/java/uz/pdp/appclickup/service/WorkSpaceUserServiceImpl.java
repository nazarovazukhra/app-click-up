package uz.pdp.appclickup.service;

import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.WorkSpaceUser;
import uz.pdp.appclickup.repository.WorkSpaceRepository;
import uz.pdp.appclickup.repository.WorkSpaceUserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class WorkSpaceUserServiceImpl implements WorkSpaceUserService {

    final WorkSpaceUserRepository workSpaceUserRepository;
    final WorkSpaceRepository workSpaceRepository;

    public WorkSpaceUserServiceImpl(WorkSpaceUserRepository workSpaceUserRepository, WorkSpaceRepository workSpaceRepository) {
        this.workSpaceUserRepository = workSpaceUserRepository;
        this.workSpaceRepository = workSpaceRepository;
    }

    @Override
    public List<WorkSpaceUser> getWorkSpaceMembersList(UUID id) {

        return workSpaceUserRepository.getAllMembersList(id);
    }

    @Override
    public List<WorkSpaceUser> getWorkSpaceGuestsList(UUID id) {
        return workSpaceUserRepository.getAllGuestsList(id);
    }
}
