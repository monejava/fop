/*
 * Copyright 1999-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id$ */

package org.apache.fop.fo.flow;

// XML
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

// FOP
import org.apache.fop.fo.FONode;
import org.apache.fop.fo.FObj;

/**
 * Class modelling the fo:multi-properties object.
 */
public class MultiProperties extends FObj {

    static boolean notImplementedWarningGiven = false;

    // used for input FO validation
    boolean hasMultiPropertySet = false;
    boolean hasWrapper = false;

    /**
     * @param parent FONode that is the parent of this object
     */
    public MultiProperties(FONode parent) {
        super(parent);

        if (!notImplementedWarningGiven) {
            getLogger().warn("fo:multi-properties is not yet implemented.");
            notImplementedWarningGiven = true;
        }
    }

    /**
     * @see org.apache.fop.fo.FONode#validateChildNode(Locator, String, String)
     * XSL Content Model: (multi-property-set+, wrapper)
     */
    protected void validateChildNode(Locator loc, String nsURI, String localName) 
        throws SAXParseException {
            if (nsURI == FO_URI && localName.equals("multi-property-set")) {
                if (hasWrapper) {
                    nodesOutOfOrderError(loc, "fo:multi-property-set", "fo:wrapper");
                } else {
                    hasMultiPropertySet = true;
                }
            } else if (nsURI == FO_URI && localName.equals("wrapper")) {
                if (hasWrapper) {
                    tooManyNodesError(loc, "fo:wrapper");
                } else {
                    hasWrapper = true;
                }
            } else {
                invalidChildError(loc, nsURI, localName);
            }
    }

    /**
     * Make sure content model satisfied, if so then tell the
     * FOEventHandler that we are at the end of the flow.
     * @see org.apache.fop.fo.FONode#end
     */
    protected void endOfNode() throws SAXParseException {
        if (!hasMultiPropertySet || !hasWrapper) {
            missingChildElementError("(multi-property-set+, wrapper)");
        }
    }

    /**
     * @see org.apache.fop.fo.FObj#getName()
     */
    public String getName() {
        return "fo:multi-properties";
    }

    /**
     * @see org.apache.fop.fo.FObj#getNameId()
     */
    public int getNameId() {
        return FO_MULTI_PROPERTIES;
    }
}
