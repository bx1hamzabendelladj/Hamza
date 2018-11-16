/*!-----------------------------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * monaco-languages version: 0.9.0(e162b4ba29044167bc7181c42b3270fa8a467424)
 * Released under the MIT license
 * https://github.com/Microsoft/monaco-languages/blob/master/LICENSE.md
 *-----------------------------------------------------------------------------*/
define('vs/basic-languages/src/bat', ['require', 'exports'], function(e, s) {
  'use strict';
  Object.defineProperty(s, '__esModule', { value: !0 }),
    (s.conf = {
      comments: { lineComment: 'REM' },
      brackets: [['{', '}'], ['[', ']'], ['(', ')']],
      autoClosingPairs: [
        { open: '{', close: '}' },
        { open: '[', close: ']' },
        { open: '(', close: ')' },
        { open: '"', close: '"' },
      ],
      surroundingPairs: [
        { open: '[', close: ']' },
        { open: '(', close: ')' },
        { open: '"', close: '"' },
      ],
    }),
    (s.language = {
      defaultToken: '',
      ignoreCase: !0,
      tokenPostfix: '.bat',
      brackets: [
        { token: 'delimiter.bracket', open: '{', close: '}' },
        { token: 'delimiter.parenthesis', open: '(', close: ')' },
        { token: 'delimiter.square', open: '[', close: ']' },
      ],
      keywords: /call|defined|echo|errorlevel|exist|for|goto|if|pause|set|shift|start|title|not|pushd|popd/,
      symbols: /[=><!~?&|+\-*\/\^;\.,]+/,
      escapes: /\\(?:[abfnrtv\\"']|x[0-9A-Fa-f]{1,4}|u[0-9A-Fa-f]{4}|U[0-9A-Fa-f]{8})/,
      tokenizer: {
        root: [
          [/^(\s*)(rem(?:\s.*|))$/, ['', 'comment']],
          [/(\@?)(@keywords)(?!\w)/, [{ token: 'keyword' }, { token: 'keyword.$2' }]],
          [/[ \t\r\n]+/, ''],
          [/setlocal(?!\w)/, 'keyword.tag-setlocal'],
          [/endlocal(?!\w)/, 'keyword.tag-setlocal'],
          [/[a-zA-Z_]\w*/, ''],
          [/:\w*/, 'metatag'],
          [/%[^%]+%/, 'variable'],
          [/%%[\w]+(?!\w)/, 'variable'],
          [/[{}()\[\]]/, '@brackets'],
          [/@symbols/, 'delimiter'],
          [/\d*\.\d+([eE][\-+]?\d+)?/, 'number.float'],
          [/0[xX][0-9a-fA-F_]*[0-9a-fA-F]/, 'number.hex'],
          [/\d+/, 'number'],
          [/[;,.]/, 'delimiter'],
          [/"/, 'string', '@string."'],
          [/'/, 'string', "@string.'"],
        ],
        string: [
          [
            /[^\\"'%]+/,
            { cases: { '@eos': { token: 'string', next: '@popall' }, '@default': 'string' } },
          ],
          [/@escapes/, 'string.escape'],
          [/\\./, 'string.escape.invalid'],
          [/%[\w ]+%/, 'variable'],
          [/%%[\w]+(?!\w)/, 'variable'],
          [
            /["']/,
            { cases: { '$#==$S2': { token: 'string', next: '@pop' }, '@default': 'string' } },
          ],
          [/$/, 'string', '@popall'],
        ],
      },
    });
});
