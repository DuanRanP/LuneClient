#ifdef GL_ES
precision highp float;
#endif

#extension GL_OES_standard_derivatives : enable

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

void main( void ) {

	vec2 st = gl_FragCoord.xy/resolution;
	gl_FragColor = vec4(st.x, st.y, .7 + .2*sin(time), 1);


}
