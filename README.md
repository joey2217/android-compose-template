# Android Template

## 参考

https://github.com/android/architecture-samples

https://developer.android.google.cn/topic/architecture?hl=zh-cn

https://yuanbao.tencent.com/chat/naQivTmsDa/6cfedaaf-ed1a-43fb-9ed6-57261819c124

## [使用 Hilt 实现依赖项注入](https://developer.android.google.cn/training/dependency-injection/hilt-android?hl=zh-cn)

### [使用 Hilt 注入 ViewModel 对象](https://developer.android.google.cn/training/dependency-injection/hilt-jetpack?hl=zh-cn#viewmodels)

```kotlin
@HiltViewModel
class MyViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ExampleRepository
) : ViewModel() { /* ... */ }

// import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun MyScreen(
    viewModel: MyViewModel = viewModel()
) {  
    
}
```

## [导航](https://developer.android.google.cn/guide/navigation?hl=zh-cn)

### [与底部导航栏集成](https://developer.android.google.cn/develop/ui/compose/navigation?hl=zh-cn#bottom-nav)

## [ViewModel](https://developer.android.google.cn/topic/libraries/architecture/viewmodel?hl=zh-cn)

### [依赖](https://developer.android.google.cn/jetpack/androidx/releases/lifecycle?hl=zh-cn#kotlin)



### 全局共享的ViewModel

```kotlin
val activity = LocalContext.current as ComponentActivity
val diceRollViewModel: DiceRollViewModel = viewModel(activity)
val diceRollUiState by diceRollViewModel.uiState.collectAsStateWithLifecycle()
```


```shell
app/
├── ui/
│   ├── screen/              # Composable 组件
│   ├── viewmodel/           # ViewModels
│   └── state/               # UI 状态类（Sealed Class）
├── domain/
│   ├── model/               # 业务模型
│   └── usecase/             # Use Cases
└── data/
    ├── repository/          # Repository 实现
    ├── local/               # 数据库（Room）
    └── remote/              # 网络（Retrofit）
```