package uz.pdp.appclickup.service;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.Space;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.SpaceDto;
import uz.pdp.appclickup.repository.AttachmentRepository;
import uz.pdp.appclickup.repository.SpaceRepository;
import uz.pdp.appclickup.repository.UserRepository;
import uz.pdp.appclickup.repository.WorkSpaceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SpaceServiceImpl implements SpaceService {

    final SpaceRepository spaceRepository;
    final UserRepository userRepository;
    final WorkSpaceRepository workSpaceRepository;
    final AttachmentRepository attachmentRepository;

    public SpaceServiceImpl(SpaceRepository spaceRepository, UserRepository userRepository, WorkSpaceRepository workSpaceRepository, AttachmentRepository attachmentRepository) {
        this.spaceRepository = spaceRepository;
        this.userRepository = userRepository;
        this.workSpaceRepository = workSpaceRepository;
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public ApiResponse addSpace(SpaceDto spaceDto, User user) {

        boolean exists = spaceRepository.existsByNameAndWorkSpaceId(spaceDto.getName(), workSpaceRepository.findById(spaceDto.getWorkSpaceId()).orElseThrow(() -> new ResourceNotFoundException("WorkSpace not found ")));

        if (exists)
            return new ApiResponse("In this user has such space", false);

        Space space = new Space(
                spaceDto.getName(),
                spaceDto.getColor(),
                spaceDto.getAvatarId() == null ? null : attachmentRepository.getOne(spaceDto.getAvatarId()),
                workSpaceRepository.findById(spaceDto.getWorkSpaceId()).orElseThrow(() -> new ResourceNotFoundException("attachment not found"))
        );
        spaceRepository.save(space);
        return new ApiResponse("Space added", true);
    }

    @Override
    public ApiResponse editSpace(UUID id, SpaceDto spaceDto, User user) {

        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (!optionalSpace.isPresent())
            return new ApiResponse("Such space not found", false);

        Space editingSpace = optionalSpace.get();

        boolean exists = spaceRepository.existsByNameAndWorkSpaceId(spaceDto.getName(), workSpaceRepository.findById(spaceDto.getWorkSpaceId()).orElseThrow(() -> new ResourceNotFoundException("WorkSpace not found ")));

        if (exists) {

            editingSpace.setColor(spaceDto.getColor());
            editingSpace.setAvatarId(attachmentRepository.getOne(spaceDto.getAvatarId()));
            spaceRepository.save(editingSpace);
            return new ApiResponse("In this workSpace has such space with this "+ spaceDto.getName()+ "  you can only change color and avatar", true);
        }

        editingSpace.setColor(spaceDto.getColor());
        editingSpace.setName(spaceDto.getName());
        editingSpace.setAvatarId(attachmentRepository.getOne(spaceDto.getAvatarId()));
        spaceRepository.save(editingSpace);
        return new ApiResponse("Space edited", true);

    }

    @Override
    public List<Space> getSpaceList(UUID worSpaceId) {

        return spaceRepository.findAllByWorkSpaceId(worSpaceId);
    }

    @Override
    public Space getOneSpaceByWorkSpaceId(UUID workSpaceId, UUID spaceId) {
        return spaceRepository.findByIdAndWorkSpaceId(spaceId, workSpaceId);
    }

    @Override
    public ApiResponse deleteSpace(UUID spaceId,UUID workSpaceId) {

        boolean existsById = spaceRepository.existsByIdAndWorkSpaceId(spaceId,workSpaceId);
        if (!existsById)
            return new ApiResponse("Such space not found in this workSpace", false);

        spaceRepository.deleteByIdAndWorkSpaceId(spaceId,workSpaceId);
        return new ApiResponse("Space deleted which was in this workSpace", true);
    }


}
