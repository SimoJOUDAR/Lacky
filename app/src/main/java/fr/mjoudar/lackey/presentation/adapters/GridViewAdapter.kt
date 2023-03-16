package fr.mjoudar.lackey.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.mjoudar.lackey.databinding.LayoutGridItemHeaterBinding
import fr.mjoudar.lackey.databinding.LayoutGridItemLightBinding
import fr.mjoudar.lackey.databinding.LayoutGridItemRollerShutterBinding
import fr.mjoudar.lackey.domain.models.Device
import fr.mjoudar.lackey.domain.models.ProductType
import fr.mjoudar.lackey.presentation.homePage.HomePageViewModel

/***************************************************************************************************
 * GridViewAdapter class - to define how our data are handled and displayed inside our RecyclerView
 ***************************************************************************************************/
class GridViewAdapter(
    private val onItemClickListener: View.OnClickListener,
    private val onDeleteClickListener : View.OnClickListener,
    private val onContextClickListener: View.OnContextClickListener,
    private val viewModel: HomePageViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var devices = listOf<Device>()

    inner class GridViewHolderLight(val binding: LayoutGridItemLightBinding) : RecyclerView.ViewHolder(binding.root)
    inner class GridViewHolderRollerShutter(val binding: LayoutGridItemRollerShutterBinding) : RecyclerView.ViewHolder(binding.root)
    inner class GridViewHolderHeater(val binding: LayoutGridItemHeaterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return when (devices[position].productType) {
            ProductType.Light -> Light_Type
            ProductType.RollerShutter -> Roller_Shutter_Type
            ProductType.Heater -> Heater_Type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            Light_Type -> {
                val binding = LayoutGridItemLightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridViewHolderLight(binding)
            }
            Roller_Shutter_Type -> {
                val binding = LayoutGridItemRollerShutterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridViewHolderRollerShutter(binding)
            }
            else -> {
                val binding = LayoutGridItemHeaterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridViewHolderHeater(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            Light_Type -> {
                val light = devices[position].toLight()
                val gridViewHolderLight : GridViewHolderLight = holder as GridViewHolderLight
                gridViewHolderLight.binding.light = light
                gridViewHolderLight.binding.viewModel = viewModel
                with(holder.itemView) {
                    tag = light
                    setOnClickListener(onItemClickListener)
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        setOnContextClickListener(onContextClickListener)
                    }
                }
                with(holder.binding.delete) {
                    tag = light
                    setOnClickListener(onDeleteClickListener)
                }
                return
            }
            Roller_Shutter_Type -> {
                val rollerShutter = devices[position].toRollerShutter()
                val gridViewHolderRollerShutter : GridViewHolderRollerShutter = holder as GridViewHolderRollerShutter
                gridViewHolderRollerShutter.binding.rollerShutter = rollerShutter
                gridViewHolderRollerShutter.binding.viewModel = viewModel
                with(holder.itemView) {
                    tag = rollerShutter
                    setOnClickListener(onItemClickListener)
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        setOnContextClickListener(onContextClickListener)
                    }
                }
                with(holder.binding.delete) {
                    tag = rollerShutter
                    setOnClickListener(onDeleteClickListener)
                }
                return
            }
            Heater_Type -> {
                val heater = devices[position].toHeater()
                val gridViewHolderHeater : GridViewHolderHeater = holder as GridViewHolderHeater
                gridViewHolderHeater.binding.heater = heater
                gridViewHolderHeater.binding.viewModel = viewModel
                with(holder.itemView) {
                    tag = heater
                    setOnClickListener(onItemClickListener)
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        setOnContextClickListener(onContextClickListener)
                    }
                }
                with(holder.binding.delete) {
                    tag = heater
                    setOnClickListener(onDeleteClickListener)
                }
                return
            }
        }
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    // Set Adapter's data from outside the class
    fun setData(data: List<Device>) {
        devices = data
    }

    companion object {
        const val Light_Type = 0
        const val Roller_Shutter_Type = 1
        const val Heater_Type = 2
    }
}