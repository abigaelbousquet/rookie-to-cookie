import {
  Select,
  _classCallCheck,
  _createClass,
  _getPrototypeOf,
  _inherits,
  _possibleConstructorReturn,
  cleanValue,
  manageState,
  require_AutosizeInput
} from "./chunk-PKUAPGWT.js";
import {
  _defineProperty,
  _extends,
  _toConsumableArray
} from "./chunk-HGPWDNKD.js";
import {
  require_react_dom
} from "./chunk-R6V7HMUL.js";
import {
  require_react
} from "./chunk-E6IAQ5KI.js";
import {
  __toESM
} from "./chunk-UXIASGQL.js";

// node_modules/react-select/creatable/dist/react-select.browser.esm.js
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
function ownKeys(object, enumerableOnly) {
  var keys = Object.keys(object);
  if (Object.getOwnPropertySymbols) {
    var symbols = Object.getOwnPropertySymbols(object);
    if (enumerableOnly)
      symbols = symbols.filter(function(sym) {
        return Object.getOwnPropertyDescriptor(object, sym).enumerable;
      });
    keys.push.apply(keys, symbols);
  }
  return keys;
}
function _objectSpread(target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i] != null ? arguments[i] : {};
    if (i % 2) {
      ownKeys(Object(source), true).forEach(function(key) {
        _defineProperty(target, key, source[key]);
      });
    } else if (Object.getOwnPropertyDescriptors) {
      Object.defineProperties(target, Object.getOwnPropertyDescriptors(source));
    } else {
      ownKeys(Object(source)).forEach(function(key) {
        Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key));
      });
    }
  }
  return target;
}
var compareOption = function compareOption2() {
  var inputValue = arguments.length > 0 && arguments[0] !== void 0 ? arguments[0] : "";
  var option = arguments.length > 1 ? arguments[1] : void 0;
  var candidate = String(inputValue).toLowerCase();
  var optionValue = String(option.value).toLowerCase();
  var optionLabel = String(option.label).toLowerCase();
  return optionValue === candidate || optionLabel === candidate;
};
var builtins = {
  formatCreateLabel: function formatCreateLabel(inputValue) {
    return 'Create "'.concat(inputValue, '"');
  },
  isValidNewOption: function isValidNewOption(inputValue, selectValue, selectOptions) {
    return !(!inputValue || selectValue.some(function(option) {
      return compareOption(inputValue, option);
    }) || selectOptions.some(function(option) {
      return compareOption(inputValue, option);
    }));
  },
  getNewOptionData: function getNewOptionData(inputValue, optionLabel) {
    return {
      label: optionLabel,
      value: inputValue,
      __isNew__: true
    };
  }
};
var defaultProps = _objectSpread({
  allowCreateWhileLoading: false,
  createOptionPosition: "last"
}, builtins);
var makeCreatableSelect = function makeCreatableSelect2(SelectComponent) {
  var _class, _temp;
  return _temp = _class = function(_Component) {
    _inherits(Creatable2, _Component);
    var _super = _createSuper(Creatable2);
    function Creatable2(props) {
      var _this;
      _classCallCheck(this, Creatable2);
      _this = _super.call(this, props);
      _this.select = void 0;
      _this.onChange = function(newValue, actionMeta) {
        var _this$props = _this.props, getNewOptionData2 = _this$props.getNewOptionData, inputValue = _this$props.inputValue, isMulti = _this$props.isMulti, onChange = _this$props.onChange, onCreateOption = _this$props.onCreateOption, value = _this$props.value, name = _this$props.name;
        if (actionMeta.action !== "select-option") {
          return onChange(newValue, actionMeta);
        }
        var newOption = _this.state.newOption;
        var valueArray = Array.isArray(newValue) ? newValue : [newValue];
        if (valueArray[valueArray.length - 1] === newOption) {
          if (onCreateOption)
            onCreateOption(inputValue);
          else {
            var newOptionData = getNewOptionData2(inputValue, inputValue);
            var newActionMeta = {
              action: "create-option",
              name
            };
            if (isMulti) {
              onChange([].concat(_toConsumableArray(cleanValue(value)), [newOptionData]), newActionMeta);
            } else {
              onChange(newOptionData, newActionMeta);
            }
          }
          return;
        }
        onChange(newValue, actionMeta);
      };
      var options = props.options || [];
      _this.state = {
        newOption: void 0,
        options
      };
      return _this;
    }
    _createClass(Creatable2, [{
      key: "UNSAFE_componentWillReceiveProps",
      value: function UNSAFE_componentWillReceiveProps(nextProps) {
        var allowCreateWhileLoading = nextProps.allowCreateWhileLoading, createOptionPosition = nextProps.createOptionPosition, formatCreateLabel2 = nextProps.formatCreateLabel, getNewOptionData2 = nextProps.getNewOptionData, inputValue = nextProps.inputValue, isLoading = nextProps.isLoading, isValidNewOption2 = nextProps.isValidNewOption, value = nextProps.value;
        var options = nextProps.options || [];
        var newOption = this.state.newOption;
        if (isValidNewOption2(inputValue, cleanValue(value), options)) {
          newOption = getNewOptionData2(inputValue, formatCreateLabel2(inputValue));
        } else {
          newOption = void 0;
        }
        this.setState({
          newOption,
          options: (allowCreateWhileLoading || !isLoading) && newOption ? createOptionPosition === "first" ? [newOption].concat(_toConsumableArray(options)) : [].concat(_toConsumableArray(options), [newOption]) : options
        });
      }
    }, {
      key: "focus",
      value: function focus() {
        this.select.focus();
      }
    }, {
      key: "blur",
      value: function blur() {
        this.select.blur();
      }
    }, {
      key: "render",
      value: function render() {
        var _this2 = this;
        var options = this.state.options;
        return import_react.default.createElement(SelectComponent, _extends({}, this.props, {
          ref: function ref(_ref) {
            _this2.select = _ref;
          },
          options,
          onChange: this.onChange
        }));
      }
    }]);
    return Creatable2;
  }(import_react.Component), _class.defaultProps = defaultProps, _temp;
};
var SelectCreatable = makeCreatableSelect(Select);
var Creatable = manageState(SelectCreatable);
var react_select_browser_esm_default = Creatable;
export {
  react_select_browser_esm_default as default,
  defaultProps,
  makeCreatableSelect
};
//# sourceMappingURL=react-select_creatable.js.map
