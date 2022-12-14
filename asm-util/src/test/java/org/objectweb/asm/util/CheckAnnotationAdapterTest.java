// ASM: a very small and fast Java bytecode manipulation framework
// Copyright (c) 2000-2011 INRIA, France Telecom
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
// 1. Redistributions of source code must retain the above copyright
//    notice, this list of conditions and the following disclaimer.
// 2. Redistributions in binary form must reproduce the above copyright
//    notice, this list of conditions and the following disclaimer in the
//    documentation and/or other materials provided with the distribution.
// 3. Neither the name of the copyright holders nor the names of its
//    contributors may be used to endorse or promote products derived from
//    this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
// THE POSSIBILITY OF SUCH DAMAGE.
package org.objectweb.asm.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.objectweb.asm.Type;
import org.objectweb.asm.test.AsmTest;

/**
 * Unit tests for {@link CheckAnnotationAdapter}.
 *
 * @author Eric Bruneton
 */
class CheckAnnotationAdapterTest extends AsmTest {

  @Test
  void testVisit_illegalAnnotationName() {
    CheckAnnotationAdapter checkAnnotationAdapter = new CheckAnnotationAdapter(null);

    Executable visit = () -> checkAnnotationAdapter.visit(null, Integer.valueOf(0));

    Exception exception = assertThrows(IllegalArgumentException.class, visit);
    assertEquals("Annotation value name must not be null", exception.getMessage());
  }

  @Test
  void testVisit_illegalAnnotationValue1() {
    CheckAnnotationAdapter checkAnnotationAdapter = new CheckAnnotationAdapter(null);

    Executable visit = () -> checkAnnotationAdapter.visit("name", new Object());

    Exception exception = assertThrows(IllegalArgumentException.class, visit);
    assertEquals("Invalid annotation value", exception.getMessage());
  }

  @Test
  void testVisit_illegalAnnotationValue2() {
    CheckAnnotationAdapter checkAnnotationAdapter = new CheckAnnotationAdapter(null);

    Executable visit = () -> checkAnnotationAdapter.visit("name", Type.getMethodType("()V"));

    Exception exception = assertThrows(IllegalArgumentException.class, visit);
    assertEquals("Invalid annotation value", exception.getMessage());
  }

  @Test
  void testVisit_afterEnd() {
    CheckAnnotationAdapter checkAnnotationAdapter = new CheckAnnotationAdapter(null);
    checkAnnotationAdapter.visitEnd();

    Executable visit = () -> checkAnnotationAdapter.visit("name", Integer.valueOf(0));

    Exception exception = assertThrows(IllegalStateException.class, visit);
    assertEquals(
        "Cannot call a visit method after visitEnd has been called", exception.getMessage());
  }

  @Test
  void testVisitEnum_illegalAnnotationEnumValue() {
    CheckAnnotationAdapter checkAnnotationAdapter = new CheckAnnotationAdapter(null);

    Executable visitEnum = () -> checkAnnotationAdapter.visitEnum("name", "Lpkg/Enum;", null);

    Exception exception = assertThrows(IllegalArgumentException.class, visitEnum);
    assertEquals("Invalid enum value", exception.getMessage());
  }
}
