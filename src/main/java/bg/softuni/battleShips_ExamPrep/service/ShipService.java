package bg.softuni.battleShips_ExamPrep.service;

import bg.softuni.battleShips_ExamPrep.model.binding.HomeBindingModel;
import bg.softuni.battleShips_ExamPrep.model.entity.ShipEntity;
import bg.softuni.battleShips_ExamPrep.model.service.ShipServiceModel;
import bg.softuni.battleShips_ExamPrep.model.view.UserShipView;
import bg.softuni.battleShips_ExamPrep.repository.ShipRepository;
import bg.softuni.battleShips_ExamPrep.security.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipService {

    private final ShipRepository shipRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;
    private final UserService userService;
    private final CategoryService categoryService;

    public ShipService(ShipRepository shipRepository, ModelMapper modelMapper, CurrentUser currentUser, UserService userService, CategoryService categoryService) {
        this.shipRepository = shipRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    public void addShip(ShipServiceModel shipServiceModel) {
        ShipEntity shipEntity = modelMapper.map(shipServiceModel, ShipEntity.class);

        shipEntity.setUser(userService.findById(currentUser.getId()));
        shipEntity.setCategory(categoryService.findByCategoryEnum(shipServiceModel.getCategory()));

        shipRepository.save(shipEntity);
    }

    public List<UserShipView> findShipsById(Long id) {
        return shipRepository.findAllByUserId(id)
                .stream().map(s -> modelMapper.map(s, UserShipView.class))
                .collect(Collectors.toList());
    }

    public List<UserShipView> findShipsOfOthers(Long id) {
        return shipRepository.findAllByUserIdNot(id)
                .stream().map(s -> modelMapper.map(s, UserShipView.class))
                .collect(Collectors.toList());
    }

    public List<UserShipView> findAll() {
        return shipRepository.findAllShipsOrderByDesc()
                .stream().map(s -> modelMapper.map(s, UserShipView.class))
                .collect(Collectors.toList());
    }

    public void fight(HomeBindingModel homeBindingModel) {

        Optional<ShipEntity> attacker = shipRepository.findById(homeBindingModel.getAttackerShip());
        Optional<ShipEntity> defender = shipRepository.findById(homeBindingModel.getDefenderShip());


        ShipEntity attackerShip = attacker.orElseThrow();
        ShipEntity defenderShip = defender.orElseThrow();

        long defenderHpAfterAttack = defenderShip.getHealth() - attackerShip.getPower();

        if (defenderHpAfterAttack <= 0) {
            shipRepository.deleteById(homeBindingModel.getDefenderShip());
        } else {

            defenderShip.setHealth(defenderHpAfterAttack);
            shipRepository.save(defenderShip);
        }

    }
}
