import {
  CacheProvider,
  Select,
  _classCallCheck,
  _createClass,
  _getPrototypeOf,
  _inherits,
  _possibleConstructorReturn,
  cache_browser_esm_default,
  components,
  createFilter,
  defaultTheme,
  manageState,
  memoize_one_esm_default,
  mergeStyles,
  require_AutosizeInput
} from "./chunk-PKUAPGWT.js";
import "./chunk-HGPWDNKD.js";
import {
  require_react_dom
} from "./chunk-R6V7HMUL.js";
import {
  require_react
} from "./chunk-E6IAQ5KI.js";
import {
  __toESM
} from "./chunk-UXIASGQL.js";

// node_modules/react-select/dist/react-select.browser.esm.js
var import_react = __toESM(require_react());
var import_react_dom = __toESM(require_react_dom());
var import_react_input_autosize = __toESM(require_AutosizeInput());
function _createSuper(Derived) {
  var hasNativeReflectConstruct = _isNativeReflectConstruct();
  return function _createSuperInternal() {
    var Super = _getPrototypeOf(Derived), result;
    if (hasNativeReflectConstruct) {
      var NewTarget = _getPrototypeOf(this).constructor;
      result = Reflect.construct(Super, arguments, NewTarget);
    } else {
      result = Super.apply(this, arguments);
    }
    return _possibleConstructorReturn(this, result);
  };
}
function _isNativeReflectConstruct() {
  if (typeof Reflect === "undefined" || !Reflect.construct)
    return false;
  if (Reflect.construct.sham)
    return false;
  if (typeof Proxy === "function")
    return true;
  try {
    Date.prototype.toString.call(Reflect.construct(Date, [], function() {
    }));
    return true;
  } catch (e) {
    return false;
  }
}
var NonceProvider = function(_Component) {
  _inherits(NonceProvider2, _Component);
  var _super = _createSuper(NonceProvider2);
  function NonceProvider2(props) {
    var _this;
    _classCallCheck(this, NonceProvider2);
    _this = _super.call(this, props);
    _this.createEmotionCache = function(nonce) {
      return cache_browser_esm_default({
        nonce
      });
    };
    _this.createEmotionCache = memoize_one_esm_default(_this.createEmotionCache);
    return _this;
  }
  _createClass(NonceProvider2, [{
    key: "render",
    value: function render() {
      var emotionCache = this.createEmotionCache(this.props.nonce);
      return import_react.default.createElement(CacheProvider, {
        value: emotionCache
      }, this.props.children);
    }
  }]);
  return NonceProvider2;
}(import_react.Component);
var index = manageState(Select);
var react_select_browser_esm_default = index;
export {
  NonceProvider,
  components,
  createFilter,
  react_select_browser_esm_default as default,
  defaultTheme,
  mergeStyles
};
//# sourceMappingURL=react-select.js.map
