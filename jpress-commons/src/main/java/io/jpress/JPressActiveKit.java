/**
 * Copyright (c) 2016-2019, Michael Yang 杨福海 (fuhai999@gmail.com).
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jpress;

import com.jfinal.plugin.activerecord.Model;
import io.jpress.commons.layer.SortModel;


public class JPressActiveKit {

    /**
     * 用于标识当前 是否选中
     */
    public static final String ACTIVE_FLAG = "isActive";

    /**
     * 标识当前对象（一般情况下是分类、菜单等）
     *
     * @param model
     */
    public static void makeItActive(Model model) {
        model.put(ACTIVE_FLAG, true);

        if (model instanceof SortModel) {
            SortModel parent = ((SortModel) model).getParent();
            if (parent != null) makeItActive((Model) parent);
        }
    }

    /**
     * Model Extension Method
     * 用于增强 Model 的方法，使其在模板里可以调用 model.isActive() 方法进行判断
     *
     * @param self
     * @return
     */
    public boolean isActive(Model self) {
        Boolean active = (Boolean) self.get(ACTIVE_FLAG);
        return active != null && active;
    }

}
