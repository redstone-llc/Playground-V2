package llc.redstone.playground.feature.functions

import feature.actionMenu.ActionsMenu
import net.minestom.server.entity.Player
import net.minestom.server.inventory.InventoryType
import net.minestom.server.inventory.click.Click.Left
import net.minestom.server.inventory.click.Click.Right
import net.minestom.server.inventory.click.ClickType
import net.minestom.server.item.Material
import llc.redstone.playground.action.actions.Return
import llc.redstone.playground.feature.housingMenu.SystemsMenu
import llc.redstone.playground.managers.getSandbox
import llc.redstone.playground.menu.*
import llc.redstone.playground.utils.PaginationList
import llc.redstone.playground.utils.err
import llc.redstone.playground.utils.isValidIdentifier
import llc.redstone.playground.utils.openChat

class FunctionsMenu : PaginationMenu(
    "Functions",
    InventoryType.CHEST_6_ROW
), Backable {
    override fun setupItems(player: Player) {
        super.setupItems(player)

        val sandbox = player.getSandbox() ?: return player.err("You are not in a sandbox!")

        addItem(
            53, menuItem(Material.LIME_WOOL) {
                if (it.click is Left) {
                    player.openChat("", "Enter function name") { name ->
                        if (name.isBlank()) return@openChat
                        if (!name.isValidIdentifier()) return@openChat player.err("Function name is not a valid identifier!")
                        if (name.length > 32) return@openChat player.err("Function name cannot be longer than 32 characters!")
                        val newFunction = Function(name = name, icon = Material.MAP)
                        newFunction.actions.add(Return())

                        sandbox.functions.add(newFunction)
                        this.open(player)
                    }
                }
            }.name("<green>Add Function")
                .description("Click to add a new function")
                .action(ClickType.LEFT_CLICK, "to add a new function")
        )
    }

    override fun paginationList(player: Player): PaginationList<MenuItem>? {
        val sandbox = player.getSandbox() ?: return null
        return PaginationList(sandbox.functions.map { function ->
            menuItem(Material.fromKey(function.icon)!!) {
                if (it.click is Left) {
                    ActionsMenu(function.actions, this).open(player)
                } else if (it.click is Right) {
                    FunctionSettingsMenu(function).open(player)
                }
            }.name("<green>${function.name}")
                .description(function.description)
                .info("Actions", "<gray>${function.actions.size} actions")
                .info(null, "")
                .info("<yellow>Variables")
                .apply {
                    function.arguments.forEach {
                        this.info("<gray>${it.first.name}", "<white>${it.second}")
                    }
                }
                .action(ClickType.LEFT_CLICK, "to edit actions")
                .action(ClickType.RIGHT_CLICK, "to edit settings")
        }, slots.size)
    }

    override fun back(player: Player) {
        SystemsMenu().open(player)
    }

    override fun backName(player: Player): String {
        return "Systems menu"
    }
}