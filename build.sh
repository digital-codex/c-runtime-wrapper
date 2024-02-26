if [[ $# -ne 1 ]]; then
  echo "Usage: build.sh <target>"
  exit 1
fi

mvn_clean="mvn clean"
echo "$mvn_clean"
if ! eval "$mvn_clean"; then
  echo "Error deleting build directory..."
  exit 1
fi
mvn_compile="mvn compile"
echo "$mvn_compile"
if ! eval "$mvn_compile"; then
  echo "Error compiling sources..."
  exit 1
fi

cmake_generate="cmake -DCMAKE_BUILD_TYPE=Debug -DCMAKE_MAKE_PROGRAM=/usr/bin/ninja -DCMAKE_C_COMPILER=/usr/bin/clang -DCMAKE_CXX_COMPILER=/usr/bin/clang++ -G Ninja -S /home/treyvon/src/c-runtime-wrapper/ -B /home/treyvon/src/c-runtime-wrapper/target/cmake-build-debug"
echo "$cmake_generate"
if ! eval "$cmake_generate"; then
  echo "Error generating cmake build system..."
  exit 1
fi
cmake_build="cmake --build /home/treyvon/src/c-runtime-wrapper/target/cmake-build-debug --target $1 -j 10"
echo "$cmake_build"
if ! eval "$cmake_build"; then
  echo "Error building target: $1..."
  exit 1
fi

mvn_install="mvn install"
echo "$mvn_install"
if ! eval "$mvn_install"; then
  echo "Error installing jar..."
  exit 1
fi