package feature.actionMenu

import llc.redstone.playground.action.Action
import llc.redstone.playground.menu.*
import net.minestom.server.entity.Player
import net.minestom.server.inventory.InventoryType
import net.minestom.server.inventory.click.Click.Left
import net.minestom.server.inventory.click.Click.Right
import llc.redstone.playground.utils.PaginationList
import net.minestom.server.item.Material
import llc.redstone.playground.feature.actionMenu.ActionEditMenu
import llc.redstone.playground.feature.actionMenu.AddActionMenu

class ActionsMenu(
    val actions: MutableList<Action>,
    private val backMenu: Menu
): PaginationMenu(
    "Actions",
    InventoryType.CHEST_6_ROW
), Backable {
    override fun setupItems(player: Player) {
        super.setupItems(player)

        super.addItem(50, menuItem(Material.PAPER) {
            AddActionMenu(this).open(player)
        }.name("<green>Add Action"))
    }

    override fun paginationList(player: Player): PaginationList<MenuItem> {
        val menuItems = ArrayList<MenuItem>()
        for ((index, action) in actions.withIndex()) {
            val item = action.createDisplayItem()
            item.action = { event ->
                if (event.click is Left && action.properties.isNotEmpty()) ActionEditMenu(
                    action,
                    this
                ).open(player)
                if (event.click is Right) {
                    actions.removeAt(index)
                    open(player)
                }
            }

            menuItems.add(item)
        }

        return PaginationList(menuItems, slots.size)
    }

    override fun back(player: Player) {
        backMenu.open(player)
    }

    override fun backName(player: Player): String {
        return backMenu.title
    }
}