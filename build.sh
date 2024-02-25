mvn="mvn clean compile"
echo "$mvn"
if ! eval "$mvn"; then
  echo "Error compiling java..."
  exit 1
fi
cmake_generate="cmake -DCMAKE_BUILD_TYPE=Debug -DCMAKE_MAKE_PROGRAM=/usr/bin/ninja -DCMAKE_C_COMPILER=/usr/bin/clang -DCMAKE_CXX_COMPILER=/usr/bin/clang++ -G Ninja -S /home/treyvon/src/glib-wrapper/ -B /home/treyvon/src/glib-wrapper/target/cmake-build-debug"
echo "$cmake_generate"
if ! eval "$cmake_generate"; then
  echo "Error generating cmake build system..."
  exit 1
fi
cmake_build="cmake --build /home/treyvon/src/glib-wrapper/target/cmake-build-debug --target GLibWrapper -j 10"
echo "$cmake_build"
if ! eval "$cmake_build"; then
  echo "Error building c..."
  exit 1
fi